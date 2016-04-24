package shook.xeem;

import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public interface BlankEditor {

    BlankObject.Builder getBuilder();

    void startQuestionEdit(int index, QuestionObject _question);

}
