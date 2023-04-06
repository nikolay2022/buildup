package project.network

import project.model.BuildStatusModel

/**
 * Created by Nikolay Yakushov on 06.04.2023
 */

interface BuildNotifier {
    fun invoke(buildStatusModel: BuildStatusModel, projectName: String)
}