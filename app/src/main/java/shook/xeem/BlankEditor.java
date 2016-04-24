package shook.xeem;

import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public interface BlankEditor {

    BlankObject.Factory getFactory();

    void startQuestionEdit(QuestionObject _question);

}
