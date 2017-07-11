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
from django.contrib.auth.views import (
    password_reset,
    password_reset_done,
    password_reset_confirm,
    password_reset_complete
)


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
    url(r'^googlechart', googlechart),
    url(r'^admin_keyword', admin_keyword),
    url(r'^admin_del_person', admin_del_person),
    url(r'^admin_del_keyword', admin_del_keyword),
    url(r'^admin_del_site', admin_del_site),
    url(r'^admin_add_site', admin_add_site),
    url(r'^user/login/$', login),
    url(r'^accounts/login/$', privateroom),
    url(r'^user/logout/$', logout),
    url(r'^user/registration/$', registration),
    url(r'^base_admin/$', base_admin),
    url(r'^myadmin/$', myadmin),
    url(r'^myadmin/delete/user/(\d+)$', delete_user),
    url(r'^myadmin/get_user_form/(\d+)$', get_user_form),
    url(r'^myadmin/create/user/(\d*)$', create_user),
    url(r'^user/edit_profile/$', edit_profile),
    url(r'^user/change_password/$', change_password),
    url(r'^user/reset_password/$', password_reset, name="password_reset"),
    url(r'^user/reset_password/confirm/(?P<uidb64>[0-9A-Za-z]+)-(?P<token>.+)/$', password_reset_confirm, name="password_reset_confirm"),
    url(r'^user/reset_password/done/$', password_reset_done, name="password_reset_done"),
    url(r'^user/reset_password/complete/$', password_reset_complete, name="password_reset_complete"),
]

if settings.DEBUG:
    # Static files (CSS, JavaScript, Images)
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT) + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)