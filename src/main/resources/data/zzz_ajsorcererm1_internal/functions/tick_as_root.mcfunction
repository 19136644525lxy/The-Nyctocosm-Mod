execute unless score @s aj.ajsorcererm1.rig_loaded matches 1 run function zzz_ajsorcererm1_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajsorcererm1:on_tick
function zzz_ajsorcererm1_internal:animations/tick