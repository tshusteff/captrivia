import json
import random

class Question:
    def __init__(self, id, question_text, options, correct_index):
        self.id = id
        self.question_text = question_text
        self.options = options
        self.correct_index = correct_index

def load_questions(filename):
    with open(filename) as file:
        data = json.load(file)
        return [Question(**q) for q in data]

def shuffle_questions(questions, n):
    q = random.sample(questions, n)
    return q
