#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @s[tag=aj.aj_cast_spell.anim.cast_spell] at @s run function aj_cast_spell:animations/cast_spell/next_frame
scoreboard players operation @s aj.aj_cast_spell.animating = .aj.animation aj.aj_cast_spell.animating