execute unless score @s aj.inculcate.rig_loaded matches 1 run function zzz_inculcate_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~180 ~
function #inculcate:on_tick
function zzz_inculcate_internal:animations/tick