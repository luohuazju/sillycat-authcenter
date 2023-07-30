import asyncio
from authapi.util.log import logger
import requests
from authapi.settings import settings
import base64
import json
from requests.auth import HTTPBasicAuth


class ConsulService:
    def __init__(self):
        self.host = settings['CONSUL_HOST']
        self.basic = HTTPBasicAuth(settings['CONSUL_USER'], settings['CONSUL_PASSWORD'])
        logger.info('host=%s', self.host)
        logger.info('consul user=%s', settings['CONSUL_USER'])
        logger.info('consul password=%s', settings['CONSUL_PASSWORD'])
        self.key = 'sillycat.config'
        self.index = 0

    async def monitor_consul(self):
        loop = asyncio.get_running_loop()
        await loop.run_in_executor(None, self.fetch_change_from_consul)

    def fetch_change_from_consul(self):
        while True:
            url = self.host + '/v1/kv/' + self.key
            if self.index != 0:
                url = url + '?index=' + str(self.index)
            logger.info('url=%s', url)
            try:
                r = requests.get(url, auth = self.basic)
                logger.info(r.json())
                create_index = r.json()[0]['CreateIndex']
                modify_index = r.json()[0]['ModifyIndex']
                if modify_index and create_index > 0:
                    if self.index > 0 and self.index != modify_index:
                        logger.info('change logic happen')
                        config_value_base64 = r.json()[0]['Value']
                        config_value = base64.b64decode(config_value_base64)
                        logger.info(config_value)
                        config_value_json = json.loads(config_value)
                        logger.info(config_value_json)
                    self.index = modify_index
                else:
                    self.index = create_index
            except requests.exceptions.Timeout:
                logger.info('timeout happen, will try again')
            except requests.exceptions.RequestException as e:
                logger.info('504 timeout from nginx side')
                logger.error(e)