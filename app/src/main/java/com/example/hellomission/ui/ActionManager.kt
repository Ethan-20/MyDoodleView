package com.example.hellomission.ui

import com.example.hellomission.base.DrawAction
import com.example.hellomission.ui.action.CircleAction
import com.example.hellomission.ui.action.LineAction
import com.example.hellomission.ui.action.RecAction
import com.example.hellomission.ui.action.StraightAction
import com.example.hellomission.utils.ShapeConstant

//用来返回action对象，避免重复创建
object ActionManager {
    val actions = HashMap<ShapeConstant,DrawAction>()
    fun getAction(shape:ShapeConstant): DrawAction{
        when(shape){
            ShapeConstant.LINE ->{
                actions.putIfAbsent(shape,LineAction())
            }
            ShapeConstant.CIRCLE->{
                actions.putIfAbsent(shape,CircleAction())
            }
            ShapeConstant.STRAIGHT->{
                actions.putIfAbsent(shape,StraightAction())
            }
            ShapeConstant.RECTANGLE->{
                actions.putIfAbsent(shape,RecAction())
            }

        }
        return actions.get(shape)!!
    }
}