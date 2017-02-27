
from django import template

register = template.Library()

# code from http://stackoverflow.com/questions/34491554/how-to-insert-the-content-of-text-file-in-django-template

@register.filter(name="read")
def read(file):
    try:
        return file.read()
    except IOError:
        return ''