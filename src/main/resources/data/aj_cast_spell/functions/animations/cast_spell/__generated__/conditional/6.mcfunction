#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add aj.aj_cast_spell.anim.cast_spell
execute if score .aj.aj_cast_spell.framerate aj.i matches ..-1 run scoreboard players set @s aj.frame 52
execute if score .aj.aj_cast_spell.framerate aj.i matches 1.. run scoreboard players set @s aj.frame 0
scoreboard players add .noScripts aj.i 0
execute if score .aj.anim_loop aj.aj_cast_spell.animating matches 0 run function aj_cast_spell:animation_loop
scoreboard players set #execute LANG_MC_INTERNAL 1