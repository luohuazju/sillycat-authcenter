from os import environ
from collections import ChainMap

# local settings
_defaults = {
    'LOG_LEVEL': '20',
    'CONSUL_HOST': 'https://consulnext.sillycat.io',
    'CONSUL_USER': 'user',
    'CONSUL_PASSWORD': 'password',
}

settings = ChainMap(environ, _defaults)
