# # Development

## Networking

Because our Django Server is running on localhost, Android cannot send requests to it. It can only send request to URLs or IPs.

Lets use `frontend/res/raw/config.properties` as our local configs 

Currently in `frontend/res/raw/configsample.properties` exists. 

1. Rename `configsample.properties` to `config.properties` and fill in your computers local ip.

2. Run Django server on 0.0.0.0:8000

   i.e `python manage.py runserver 0.0.0.0:8000`

3. Fill `api_url ` in the config file with your IP

   i.e `http://192.168.1.70:8000/api`