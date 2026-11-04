package com.ninekingdom.protokotlin

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.hypot
import kotlin.math.min
import kotlin.math.max

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(GameView())
    }

    inner class GameView : View(this) {
        private val ink = Paint(3)
        private var px = 155f
        private var py = 930f
        private var phase = 0
        private var slimeHp = 3
        private var dx = 0f
        private var dy = 0f
        private var lastFrame = 0L
        private var status = "Procure a Cuidadora Mel."
        private val treeList = listOf(85f to 380f, 545f to 390f, 95f to 665f, 650f to 555f, 470f to 970f, 615f to 1020f, 125f to 1100f)
        private fun color(v: String) = Color.parseColor(v)
        private fun rect(c:Canvas, l:Float,t:Float,r:Float,b:Float,hex:String,round:Float=0f) {
            ink.color=color(hex); ink.style=Paint.Style.FILL
            c.drawRoundRect(RectF(l,t,r,b),round,round,ink)
        }
        private fun circle(c:Canvas,x:Float,y:Float,r:Float,hex:String) {
            ink.color=color(hex); ink.style=Paint.Style.FILL; c.drawCircle(x,y,r,ink)
        }
        private fun txt(c:Canvas,s:String,x:Float,y:Float,size:Float,hex:String,center:Boolean=false) {
            ink.color=color(hex); ink.style=Paint.Style.FILL
            ink.textSize=size; ink.typeface=android.graphics.Typeface.create("sans-serif",android.graphics.Typeface.BOLD)
            ink.textAlign=if (center) Paint.Align.CENTER else Paint.Align.LEFT
            c.drawText(s,x,y,ink)
        }
        private fun dist(x1:Float,y1:Float,x2:Float,y2:Float)=hypot(x1-x2,y1-y2)
        private fun interact() {
            if (phase==0) {
                if (dist(px,py,285f,490f)<112f) {phase=1;status="Mel: derrote o Slime!"}
                else status="Aproxime-se de Mel para conversar."
            } else if (phase==1) {
                if (dist(px,py,533f,690f)<112f) {
                    slimeHp--; status="Golpe no Slime! Vida: $slimeHp/3"
                    if(slimeHp<=0) {phase=2;status="Missão concluída! +30 XP"}
                } else status="Chegue perto do Slime para atacar."
            } else {phase=0;slimeHp=3;px=155f;py=930f;status="Nova aventura: encontre Mel!"}
        }
        override fun onDraw(canvas:Canvas) {
            super.onDraw(canvas)
            val t=System.nanoTime()
            val dt=if(lastFrame==0L) 0f else min(.05f,(t-lastFrame)/1_000_000_000f)
            lastFrame=t
            px=(px+dx*260*dt).coerceIn(40f,680f)
            py=(py+dy*260*dt).coerceIn(220f,1050f)
            val sc=canvas.save();canvas.scale(width/720f,height/1280f)
            rect(canvas,0f,0f,720f,1280f,"#608c68")
            rect(canvas,0f,275f,720f,1100f,"#72a679",40f)
            rect(canvas,194f,310f,370f,1100f,"#d0b987",55f)
            rect(canvas,125f,305f,450f,425f,"#e4cda0",38f)
            rect(canvas,140f,236f,425f,375f,"#a9644d",15f)
            txt(canvas,"ESTALAGEM",282f,348f,21f,"#fff0bf",true)
            treeList.forEach { (x,y)-> circle(canvas,x,y,45f,"#265c44");circle(canvas,x-10,y-10,32f,"#397d4c") }
            circle(canvas,556f,480f,55f,"#65aabd")
            circle(canvas,285f,490f,26f,"#ffc680");rect(canvas,267f,511f,304f,544f,"#664c96",12f)
            txt(canvas,"Mel",285f,458f,24f,"#ffffff",true)
            if(phase!=2){circle(canvas,533f,690f,34f,"#a8df65");circle(canvas,521f,684f,5f,"#163e35");circle(canvas,546f,684f,5f,"#163e35");txt(canvas,"Slime $slimeHp/3",533f,641f,21f,"#ffffff",true)}
            circle(canvas,px,py,29f,"#3e8cd1");circle(canvas,px,py-12f,14f,"#ffe0ac")
            rect(canvas,0f,0f,720f,213f,"#132b3c",0f)
            txt(canvas,"NINE KINGDOM",28f,60f,39f,"#ffe4a1")
            txt(canvas,"KOTLIN NATIVO · ANDROID CANVAS",28f,95f,21f,"#b5d8ce")
            rect(canvas,25f,117f,290f,145f,"#3d5061",13f);rect(canvas,25f,117f,290f,145f,"#e66c71",13f)
            txt(canvas,"VIDA 100/100",35f,138f,18f,"#ffffff")
            txt(canvas,"MISSAO: "+if(phase==0) "Falar com Mel" else if(phase==1) "Vencer Slime" else "Concluída",26f,183f,22f,"#ffe0a3")
            rect(canvas,10f,1090f,710f,1275f,"#112a3c",25f)
            txt(canvas,status,360f,1127f,20f,"#ffffff",true)
            drawButton(canvas,105f,1203f,"◀");drawButton(canvas,210f,1160f,"▲");drawButton(canvas,210f,1240f,"▼")
            drawButton(canvas,315f,1203f,"▶");drawButton(canvas,600f,1200f,"AÇÃO")
            if(phase==2) txt(canvas,"AÇÃO para reiniciar",358f,1068f,20f,"#ffffff",true)
            canvas.restoreToCount(sc)
            postInvalidateOnAnimation()
        }
        private fun drawButton(c:Canvas,x:Float,y:Float,s:String){circle(c,x,y,42f,"#33556c");txt(c,s,x,y+9f,if(s=="AÇÃO") 19f else 26f,"#ffffff",true)}
        private fun dir(x:Float,y:Float):Pair<Float,Float> = when {
            dist(x,y,105f,1203f)<53 -> -1f to 0f
            dist(x,y,315f,1203f)<53 -> 1f to 0f
            dist(x,y,210f,1160f)<53 -> 0f to -1f
            dist(x,y,210f,1240f)<53 -> 0f to 1f
            else -> 0f to 0f
        }
        override fun onTouchEvent(e: MotionEvent):Boolean {
            val x=e.x*720f/width;val y=e.y*1280f/height
            when(e.actionMasked){
                MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN->{
                    if(dist(x,y,600f,1200f)<64)interact()
                    val d=dir(x,y);dx=d.first;dy=d.second
                }
                MotionEvent.ACTION_MOVE->{val d=dir(x,y);dx=d.first;dy=d.second}
                MotionEvent.ACTION_UP,MotionEvent.ACTION_POINTER_UP,MotionEvent.ACTION_CANCEL->{dx=0f;dy=0f}
            }
            return true
        }
    }
}
