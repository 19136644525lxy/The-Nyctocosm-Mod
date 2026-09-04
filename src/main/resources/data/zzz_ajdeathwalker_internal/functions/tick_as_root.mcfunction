execute unless score @s aj.ajdeathwalker.rig_loaded matches 1 run function zzz_ajdeathwalker_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajdeathwalker:on_tick
function zzz_ajdeathwalker_internal:animations/tick