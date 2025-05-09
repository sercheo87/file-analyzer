package com.sercheo.fileanalyzer

import com.intellij.openapi.actionSystem.ActionUpdateThread
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
            val lines = countLines(project, file)
            val size = getSizeFile(file)
            Messages.showMessageDialog(project, "The selected file has $lines lines and your size is $size.", "Lines Count", Messages.getInformationIcon())
        } else {
            Messages.showMessageDialog(project, "No file selected!", "Information", Messages.getInformationIcon())
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val selectedFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)

        val visible = project != null && selectedFiles != null && selectedFiles.isNotEmpty() && selectedFiles.size == 1 && !selectedFiles[0].isDirectory

        e.presentation.isEnabledAndVisible = visible
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    private fun countLines(project: com.intellij.openapi.project.Project, file: VirtualFile): String {
        val document = com.intellij.openapi.fileEditor.FileDocumentManager.getInstance().getDocument(file)

        if (document != null) {
            return formatNumber(document.lineCount)
        }

        return "0"
    }

    private fun getSizeFile(file: VirtualFile): String {
        val size = file.length
        return when {
            size < 1024 -> formatNumber(size.toInt()) + " B"
            size < 1024 * 1024 -> formatNumber((size / 1024).toInt()) + " KB"
            else -> formatNumber((size / 1024 / 1024).toInt()) + " MB"
        }
    }

}
