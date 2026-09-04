execute unless score @s aj.ajemissary.rig_loaded = @s aj.ajemissary.rig_loaded run function animated_java:ajemissary/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajemissary/zzzzzzzz/animations/tick
function #animated_java:ajemissary/on_tick/as_root