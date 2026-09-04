execute unless score @s aj.test.rig_loaded matches 1 run function zzz_test_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~180 ~
function #test:on_tick
function zzz_test_internal:animations/tick