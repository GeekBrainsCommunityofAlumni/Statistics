from django.shortcuts import render, HttpResponseRedirect, get_object_or_404
from UserManagement.models import Person
from UserManagement.forms import MyRegistrationForm
from django.http import Http404, JsonResponse
from django.template import loader
from django.template.context_processors import csrf
from django.contrib.auth.forms import UserCreationForm
# from django.contrib.auth.decorators import user_passes_test


# доступ у админке только суперпользователю
# @user_passes_test(lambda u: u.is_superuser)


def myadmin(request):
    users = Person.objects.all()
    user_form = MyRegistrationForm(UserCreationForm)
    return render(request, 'myadmin.html', {'users': users, 'form': user_form})


def delete_user(request, user_id):
    user = get_object_or_404(Person, id=user_id)
    user.delete()
    return HttpResponseRedirect('/admin/')


def get_user_form(request, user_id):
    """
    Возвращает заполненную форму для редактирования Пользователя(Person) с заданным user_id
    """
    if request.is_ajax():
        user = get_object_or_404(Person, id=user_id)
        user_form = MyRegistrationForm(instance=user)
        context = {'form': user_form, 'id': user_id}
        context.update(csrf(request))
        html = loader.render_to_string('inc-registration_form.html', context)
        data = {'errors': False, 'html': html}
        return JsonResponse(data)
    raise Http404


def create_user(request, user_id=None):
    """
    Создает Пользователя(User)
    Или редактирует существующего, если указан  user_id
    """
    if request.is_ajax():
        print('user_id = ', user_id)
        if not user_id:
            print('Not user_id')
            user = Person(request.POST)
        else:
            user = get_object_or_404(Person, id=user_id)
            user = MyRegistrationForm(request.POST or None, instance=user)
        if user.is_valid():
            user.save()
            users = Person.objects.all()
            html = loader.render_to_string('inc-users_list.html', {'users': users}, request=request)
            data = {'errors': False, 'html': html}
            return JsonResponse(data)
        else:
            errors = user.errors.as_json()
            return JsonResponse({'errors': errors})
    raise Http404


# Demo-views
def send_json(request):
    # Если данные были отправлены ajax'ом
    if request.is_ajax():
        # Данные хранятся также в атрибуте POST или GET, в зависимости от методы отправки данных
        request_data = request.POST
        # Словарик при отправке автоматически будет преобразован к json
        send_data = {'key': 'value'}
        return JsonResponse(send_data)
    raise Http404
