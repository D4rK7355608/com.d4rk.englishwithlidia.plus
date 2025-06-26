import xml.etree.ElementTree as ET
import re
import os
from googletrans import Translator

LANG_MAP = {
    'ar-rEG': 'ar',
    'bg-rBG': 'bg',
    'bn-rBD': 'bn',
    'de-rDE': 'de',
    'es-rGQ': 'es',
    'es-rMX': 'es',
    'fil-rPH': 'tl',
    'fr-rFR': 'fr',
    'hi-rIN': 'hi',
    'hu-rHU': 'hu',
    'in-rID': 'id',
    'it-rIT': 'it',
    'ja-rJP': 'ja',
    'ko-rKR': 'ko',
    'pl-rPL': 'pl',
    'pt-rBR': 'pt',
    'ro-rRO': 'ro',
    'ru-rRU': 'ru',
    'sv-rSE': 'sv',
    'th-rTH': 'th',
    'tr-rTR': 'tr',
    'uk-rUA': 'uk',
    'ur-rPK': 'ur',
    'vi-rVN': 'vi',
    'zh-rTW': 'zh-tw',
}

SOURCE_XML = os.path.join('app', 'src', 'main', 'res', 'values', 'strings.xml')

def protect(text):
    pattern = re.compile(r'<a[^>]+>[^<]*</a>')
    placeholders = {}
    def repl(m):
        idx = len(placeholders)
        placeholders[str(idx)] = m.group(0)
        return f'{{{idx}}}'
    return pattern.sub(repl, text), placeholders

def restore(text, placeholders):
    for idx, value in placeholders.items():
        text = text.replace(f'{{{idx}}}', value)
    return text

def main():
    tree = ET.parse(SOURCE_XML)
    root = tree.getroot()
    strings = [(e.attrib['name'], e.text or '') for e in root.findall('string')]
    translator = Translator(service_urls=['translate.googleapis.com'], raise_exception=True)
    for locale, code in LANG_MAP.items():
        dir_path = os.path.join('app', 'src', 'main', 'res', f'values-{locale}')
        os.makedirs(dir_path, exist_ok=True)
        out_file = os.path.join(dir_path, 'strings.xml')
        texts = []
        holders = []
        for name, text in strings:
            t, ph = protect(text)
            texts.append(t)
            holders.append(ph)
        translations = []
        for i in range(0, len(texts), 5):
            part = translator.translate(texts[i:i+5], dest=code)
            if isinstance(part, list):
                translations.extend(part)
            else:
                translations.append(part)
        with open(out_file, 'w', encoding='utf-8') as f:
            f.write('<?xml version="1.0" encoding="utf-8"?>\n<resources>\n')
            for (name, _), tr, ph in zip(strings, translations, holders):
                translated_text = restore(tr.text, ph)
                f.write(f'    <string name="{name}">{translated_text}</string>\n')
            f.write('</resources>\n')

if __name__ == '__main__':
    main()
