import hashlib


def generate_md5(original):
    hl = hashlib.md5()
    hl.update(original.encode(encoding='utf-8'))
    return hl.hexdigest()