from django.shortcuts import render

# Create your views here.

def main(request):
    return render(request, 'index.html')

def daily_statistics(request):
    return render(request, 'daily_statistics.html')

def periodic_statistics(request):
    return render(request, 'periodic_statistics.html')

def registration(request):
    return render(request, 'registration.html')

def privateroom(request):
    return render(request, 'privateroom.html')

def authorization(request):
    return render(request, 'authorization.html')

def partnership(request):
    return render(request, 'partnership.html')

def contacts(request):
    return render(request, 'contacts.html')

def review(request):
    return render(request, 'review.html')

def support(request):
    return render(request, 'support.html')