from django import forms
from source_manager.models import Sites


class ParametrizedStatForm(forms.ModelForm):
    name = forms.CharField(max_length=2048)

    class Meta:
        model = Sites
        fields = ('__all__')
