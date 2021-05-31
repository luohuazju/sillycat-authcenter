import jwt
from authapi.util.log import logger
from fastapi.encoders import jsonable_encoder
from authapi.exception.authapi_exception import AuthAPIException


class JwtService:

    def __init__(self):
        raw_private_key = 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKCt+08AzetlMr93AZOegAkguTwazH6U0ZfwjAeEZbc9JzXoZj87EKPJck5oAKeqzd8Qbq57Ts/siEUgnQsJ0qSukqe+4fow24XyG4yv3v3n1QOiyiAIqk0cXws9UqkV5rJthsUUu7wqVLfEc+YgC2EkFqbjckMJPmJfUIqKwoTAgMBAAECgYAj6o0bHFIBZ9liJkWYtz1wvefgLEdFHPqYVDf7+nDLi9VjoTcQUwMuOSL/I6sAG/CTD1siOLp0i1JJuZityxx0XAux2PD+uE/GmoDWtt+2Yn61098MvQ+rx1kbz/GcmgUUMquj6ktpJWtUmAQ4a/91M3yVuMNb11bejlkmDAHgMQJBAPAAi+ulYpF2YJ7PY9PoScnLdWS6nc96ZfZHN190I43g9ooXRgailehrcgp0jWRkYEzmp0NOkv/JnbI0/L2IVdcCQQC+aN22bS2WE/TFl+fHg/X9bOOLZPkyBxsS5G58F1U5TJ/TrcUig6qYj2FUHPV5HDSZ07CtnG2PbhOOcdl1Bi4lAkEApAVrmqdTnuANFlvb+LW2wA+pcibLtUEML+zp3fVsWwlU1HklZWF2G/paXOTcwLCM0+GKjEhF1EQA3wqxdAKZzwJAf0pTjbUHGKFWrRGUHUkWlcjJhVKHVO5zJvoW8lYW3yteXYB5nU0wKrUPd8+0OrakY4GlRyqgA5au9DcgDJ+JEQJBAMF5A/i0LLGwTSJURG80n+Fgu9tgxmw+QIj4dNbtEjoWEQomZ/EP/d/NKsrkxYmaj+1oiBhfsb8ZfGwLloxvgJ4='
        raw_private_key = '-----BEGIN PRIVATE KEY-----\n' + raw_private_key + '\n-----END PRIVATE KEY-----'
        raw_public_key = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCygrftPAM3rZTK/dwGTnoAJILk8Gsx+lNGX8IwHhGW3PSc16GY/OxCjyXJOaACnqs3fEG6ue07P7IhFIJ0LCdKkrpKnvuH6MNuF8huMr97959UDosogCKpNHF8LPVKpFeaybYbFFLu8KlS3xHPmIAthJBam43JDCT5iX1CKisKEwIDAQAB'
        raw_public_key = '-----BEGIN PUBLIC KEY-----\n' + raw_public_key + '\n-----END PUBLIC KEY-----'
        self.private_key = raw_private_key.encode('ascii')
        self.public_key = raw_public_key.encode('ascii')

    def sign_token(self, payload):
        logger.info("sign payload is -------")
        logger.info(payload)
        json_payload = jsonable_encoder(payload)
        return jwt.encode(json_payload, self.private_key, algorithm='RS256')

    def verify_token(self, token):
        try:
            return jwt.decode(token, self.public_key, algorithms='RS256')
        except Exception as e:
            logger.error(f'Error {e}')
            raise AuthAPIException('invalid_token', 'invalid token detected')
