execute unless score @s aj.ajdeathwalker.rig_loaded = @s aj.ajdeathwalker.rig_loaded run function animated_java:ajdeathwalker/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajdeathwalker/zzzzzzzz/animations/tick
function #animated_java:ajdeathwalker/on_tick/as_root