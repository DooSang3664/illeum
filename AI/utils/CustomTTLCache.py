from cachetools import Cache, TTLCache


class CustomTTLCache(TTLCache):
    def __setitem__(self, key, value, cache_setitem=Cache.__setitem__):
        if value is not None:
            super(CustomTTLCache, self).__setitem__(key, value, cache_setitem)