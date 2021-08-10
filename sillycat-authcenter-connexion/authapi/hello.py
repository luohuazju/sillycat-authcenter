def index() -> str:
    methods = [
        {'url': '/', 'method': '[get]', 'name': 'root', 'desc': 'fetch all api info', 'required': None},
        {'url': '/auth/login', 'method': '[post]', 'name': 'Logon', 'desc': 'get user token',
         'required': 'username(str)，password(str)'},
        {'url': '/hello', 'method': '[get]', 'name': 'say hello', 'desc': 'say hello',
         'required': 'token'},
    ]
    string = ''
    for method in methods:
        string += f"{method['url']} {method['method']}, {method['name']}," \
                  f" {method['desc']}, required: {method['required']} \r\n"
    return string


def hello() -> str:
    return "hello Carl"
