import connexion


app = connexion.FlaskApp(__name__, specification_dir='openapi/')
# app = connexion.FlaskApp(__name__, specification_dir='openapi/', server='tornado')
app.add_api('auth_api.yaml', arguments={
    'title': 'Authentication API',
    'version': 'v1.0'
})
app.run(host='0.0.0.0', port=8000)
