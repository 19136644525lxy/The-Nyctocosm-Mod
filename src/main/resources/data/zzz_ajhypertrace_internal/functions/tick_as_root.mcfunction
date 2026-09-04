execute unless score @s aj.ajhypertrace.rig_loaded matches 1 run function zzz_ajhypertrace_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajhypertrace:on_tick
function zzz_ajhypertrace_internal:animations/tick