from django.db import models
from urllib import request

URL = 'http://94.130.27.143:8080/api/person'
dict_json = request.urlopen(URL)
for i in dict_json:
    print(i)
# Create your models here.
