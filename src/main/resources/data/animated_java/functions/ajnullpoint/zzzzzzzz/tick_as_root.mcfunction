execute unless score @s aj.ajnullpoint.rig_loaded = @s aj.ajnullpoint.rig_loaded run function animated_java:ajnullpoint/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajnullpoint/zzzzzzzz/animations/tick
function #animated_java:ajnullpoint/on_tick/as_root