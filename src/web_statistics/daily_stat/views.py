from django.shortcuts import render
from django.shortcuts import render_to_response,Http404, get_object_or_404
from django.shortcuts import render, HttpResponseRedirect
from .models import *
from django.contrib.auth.decorators import login_required
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger


@login_required(login_url='/index/')
def daily_stat(request):

    tasks = Tasks.objects.filter()
    categorys = Category.objects.all()
    statuses = Status.objects.all()
    paginator = Paginator(tasks, 3)
    page = request.GET.get('page')
    try:
        tasks = paginator.page(page)
    except PageNotAnInteger:
        # If page is not an integer, deliver first page.
        tasks = paginator.page(1)
    except EmptyPage:
        # If page is out of range (e.g. 9999), deliver last page of results.
        tasks = paginator.page(paginator.num_pages)
    return render(request, 'dashboard.html', {'categorys': categorys, 'tasks': tasks, 'statuses': statuses})

