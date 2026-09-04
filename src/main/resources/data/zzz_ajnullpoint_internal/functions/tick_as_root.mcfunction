execute unless score @s aj.ajnullpoint.rig_loaded matches 1 run function zzz_ajnullpoint_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajnullpoint:on_tick
function zzz_ajnullpoint_internal:animations/tick