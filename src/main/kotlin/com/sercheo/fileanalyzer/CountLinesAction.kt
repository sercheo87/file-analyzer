package com.sercheo.fileanalyzer

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.sercheo.fileanalyzer.helpers.formatNumber

class CountLinesAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(PlatformDataKeys.VIRTUAL_FILE)

        if (file != null && file.isValid) {
            countLines(project, file)
        } else {
            Messages.showMessageDialog(project, "No file selected!", "Information", Messages.getInformationIcon())
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val selectedFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)

        val visible = project != null && selectedFiles != null && selectedFiles.size == 1 && !selectedFiles[0].isDirectory

        e.presentation.isEnabledAndVisible = visible
    }

    private fun countLines(project: com.intellij.openapi.project.Project, file: VirtualFile) {
        val document = com.intellij.openapi.fileEditor.FileDocumentManager.getInstance().getDocument(file)

        if (document != null) {
            val lines = formatNumber(document.lineCount)
            Messages.showMessageDialog(project, "The selected file has $lines lines.", "Lines Count", Messages.getInformationIcon())
        }
    }

}