execute unless score @s aj.ajsorcererm2.rig_loaded = @s aj.ajsorcererm2.rig_loaded run function animated_java:ajsorcererm2/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajsorcererm2/zzzzzzzz/animations/tick
function #animated_java:ajsorcererm2/on_tick/as_root