#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.i 1
execute at @s run function aj_cast_spell:summon/__generated__/execute/7
tag @s remove new
scoreboard players set @s aj.aj_cast_spell.cast_spell.loopMode 1
scoreboard players set @s aj.frame 0
scoreboard players set @s aj.aj_cast_spell.animating 0