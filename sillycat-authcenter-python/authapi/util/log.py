import logging
from authapi import settings


logger = logging.getLogger()
logger.handlers = []


IS_LOCAL = False

try:
    if IS_LOCAL:
        logger.setLevel(logging.DEBUG)
    else:
        logger.setLevel(int(settings['LOG_LEVEL']))
except:
    logger.setLevel(logging.INFO)

ch = logging.StreamHandler()


custom_format = '{asctime:s} - {levelname:s} - [{filename:s}:{lineno:d} - {funcName:>20s}() ] - {message:s}'
formatter = logging.Formatter(fmt=custom_format, style='{')
ch.setFormatter(formatter)
logger.addHandler(ch)
