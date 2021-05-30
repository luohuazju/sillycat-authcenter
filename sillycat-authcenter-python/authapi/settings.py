from os import environ
from collections import ChainMap

# local settings
_defaults = {
    'LOG_LEVEL': '20',
}

settings = ChainMap(environ, _defaults)
