execute unless score @s aj.ajemissary_vfx.rig_loaded = @s aj.ajemissary_vfx.rig_loaded run function animated_java:ajemissary_vfx/zzzzzzzz/on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function animated_java:ajemissary_vfx/zzzzzzzz/animations/tick
function #animated_java:ajemissary_vfx/on_tick/as_root