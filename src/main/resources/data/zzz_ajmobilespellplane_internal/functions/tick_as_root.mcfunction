execute unless score @s aj.ajmobilespellplane.rig_loaded matches 1 run function zzz_ajmobilespellplane_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajmobilespellplane:on_tick
function zzz_ajmobilespellplane_internal:animations/tick