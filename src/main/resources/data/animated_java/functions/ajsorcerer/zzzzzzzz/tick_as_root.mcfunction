execute unless score @s aj.ajsorcerer.rig_loaded = @s aj.ajsorcerer.rig_loaded run function animated_java:ajsorcerer/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajsorcerer/zzzzzzzz/animations/tick
function #animated_java:ajsorcerer/on_tick/as_root