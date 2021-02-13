# # Development

## Networking

Because our Django Server is running on localhost, Android cannot send requests to it. It can only send request to URLs or IPs.

Lets use `local.properties` as our local configs. This file exists in the top level of `/frontend`

1. Get your computer's local ip. With ipconfig on the Windows terminal.

2. Add a line `in local.properties`

> API_URL="http://YOUR_IP:8000/"

2. Run Django server on 0.0.0.0:8000

   i.e `python manage.py runserver 0.0.0.0:8000`

3. You can get the `API_URL` from `Helper.getBaseURL()`

4. Use this url as the 'base url' when working with Retrofit