scoreboard players add @s aj.ajdeathwalker.animation.slam.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajdeathwalker.animation.slam.local_anim_time
function animated_java:ajdeathwalker/zzzzzzzz/animations/slam/apply_frame_as_root
execute if score @s aj.ajdeathwalker.animation.slam.local_anim_time matches 90.. run function animated_java:ajdeathwalker/zzzzzzzz/animations/slam/end