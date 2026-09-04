execute unless score @s aj.ajsorcerer.rig_loaded matches 1 run function zzz_ajsorcerer_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajsorcerer:on_tick
function zzz_ajsorcerer_internal:animations/tick