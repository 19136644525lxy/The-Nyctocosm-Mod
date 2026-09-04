scoreboard players set @s aj.ajdeathwalker.animation.slam.local_anim_time 0
tag @s remove aj.ajdeathwalker.animation.slam
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajdeathwalker.disable_command_keyframes
function animated_java:ajdeathwalker/zzzzzzzz/animations/slam/tree/leaf_0
tag @s remove aj.ajdeathwalker.disable_command_keyframes