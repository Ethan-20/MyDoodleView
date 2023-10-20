package com.example.hellomission.ui

import com.example.hellomission.base.DrawAction
import com.example.hellomission.ui.action.CircleAction
import com.example.hellomission.ui.action.LineAction
import com.example.hellomission.ui.action.RecAction
import com.example.hellomission.ui.action.StraightAction
import com.example.hellomission.bean.DrawerType

//用来返回action对象，避免重复创建
object ActionManager {
    val actions = HashMap<DrawerType, DrawAction>()
    fun getAction(shape: DrawerType): DrawAction {
        when (shape) {
            DrawerType.LINE -> {
                actions.putIfAbsent(shape, LineAction())
            }

            DrawerType.CIRCLE -> {
                actions.putIfAbsent(shape, CircleAction())
            }

            DrawerType.STRAIGHT -> {
                actions.putIfAbsent(shape, StraightAction())
            }

            DrawerType.RECTANGLE -> {
                actions.putIfAbsent(shape, RecAction())
            }

        }
        return actions[shape]!!
    }
}