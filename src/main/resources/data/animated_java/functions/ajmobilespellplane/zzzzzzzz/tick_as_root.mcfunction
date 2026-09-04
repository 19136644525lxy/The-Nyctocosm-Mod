execute unless score @s aj.ajmobilespellplane.rig_loaded = @s aj.ajmobilespellplane.rig_loaded run function animated_java:ajmobilespellplane/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajmobilespellplane/zzzzzzzz/animations/tick
function #animated_java:ajmobilespellplane/on_tick/as_root