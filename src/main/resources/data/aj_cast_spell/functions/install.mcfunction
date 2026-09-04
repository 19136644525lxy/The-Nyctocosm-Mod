#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.frame dummy
scoreboard objectives add aj.aj_cast_spell.animating dummy
scoreboard objectives add aj.aj_cast_spell.cast_spell.loopMode dummy
function aj_cast_spell:reset_animation_flags
scoreboard players set #uninstall aj.i 0
scoreboard players set .aj.aj_cast_spell.framerate aj.i 1
tellraw @a [{"text":"[ ","color":"dark_gray"},{"text":"AJ","color":"aqua"},{"text":" → ","color":"gray"},{"text":"Install ⊻","color":"green"},{"text":" ]","color":"dark_gray"},"\n",{"text":"Installed ","color":"gray"},{"text":"armor_stand","color":"gold"},{"text":" rig.","color":"gray"},"\n",{"text":"◘ ","color":"gray"},{"text":"Included Scoreboard Objectives:","color":"green"},"\n",{"text":"   ◘ ","color":"gray"},{"text":"aj.i","color":"aqua"},{"text":" (Internal)","color":"dark_gray"},"\n",{"text":"   ◘ ","color":"gray"},{"text":"aj.id","color":"aqua"},{"text":" (ID)","color":"dark_gray"},"\n",{"text":"   ◘ ","color":"gray"},{"text":"aj.frame","color":"aqua"},{"text":" (Frame)","color":"dark_gray"},"\n",{"text":"   ◘ ","color":"gray"},{"text":"aj.aj_cast_spell.animating","color":"aqua"},{"text":" (Animation Flag)","color":"dark_gray"},[["\n",{"text":"   ◘ ","color":"gray"},{"text":"aj.aj_cast_spell.cast_spell.loopMode","color":"aqua"},{"text":" (Loop Mode)","color":"dark_gray"}]]]