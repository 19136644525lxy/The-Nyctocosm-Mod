#built using mc-build (https://github.com/mc-build/mc-build)

execute if score @s aj.aj_cast_spell.cast_spell.loopMode matches 0 run function aj_cast_spell:animations/cast_spell/stop
execute if score @s aj.aj_cast_spell.cast_spell.loopMode matches 1 run function aj_cast_spell:animations/cast_spell/pause
execute if score @s aj.aj_cast_spell.cast_spell.loopMode matches 2 run function aj_cast_spell:animations/cast_spell/__generated__/execute/503