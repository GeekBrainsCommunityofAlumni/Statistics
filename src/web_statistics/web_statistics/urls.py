"""web_statistics URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from django.conf import settings
from django.conf.urls.static import static
from main_app.views import *
from UserManagement.views import *
from AdminManagement.views import *

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^$', main),
    url(r'^about/$', about),
    url(r'^daily_statistics/$', daily_statistics),
    url(r'^periodic_statistics/$', periodic_statistics),
    url(r'^admin_statistics/$', admin_statistics),
    url(r'^registration/$', registration),
    url(r'^privateroom/$', privateroom),
    url(r'^sitemap/$', sitemap),
    url(r'^faq/$', faq),
    url(r'^politics/$', politics),
    url(r'^business/$', business),
    url(r'^economics_and_finances/$', economics_and_finances),
    url(r'^society/$', society),
    url(r'^userinfo/$', privateroom),
    url(r'^authorization/$', authorization),
    url(r'^partnership/$', partnership),
    url(r'^contacts/$', contacts),
    url(r'^review/$', review),
    url(r'^support/$', support),
    url(r'^common_statistics', common_statistics),
    url(r'^private_settings', private_settings),
    url(r'^googlechart', googlechart),
    url(r'^admin_keyword', admin_keyword)
]

urlpatterns += [
    url(r'^user/login/$', login),
    url(r'^user/logout/$', logout),
    url(r'^user/registration/$', registration),
    url(r'^base_admin/$', base_admin),
    url(r'^myadmin/$', myadmin),
    url(r'^myadmin/delete/user/(\d+)$', delete_user),
    url(r'^myadmin/get_user_form/(\d+)$', get_user_form),
    url(r'^myadmin/create/user/(\d*)$', create_user),
    url(r'^user/set_new_password/$', set_new_password),
]

if settings.DEBUG:
    # Static files (CSS, JavaScript, Images)
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT) + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)