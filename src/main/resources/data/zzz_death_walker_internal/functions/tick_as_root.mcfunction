execute unless score @s aj.death_walker.rig_loaded matches 1 run function zzz_death_walker_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~180 ~
function #death_walker:on_tick
function zzz_death_walker_internal:animations/tick