scoreboard players add @s aj.ajdeathwalker.animation.slam.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajdeathwalker.animation.slam.local_anim_time
function zzz_ajdeathwalker_internal:animations/slam/apply_frame_as_root
execute if score @s aj.ajdeathwalker.animation.slam.local_anim_time matches 90.. run function zzz_ajdeathwalker_internal:animations/slam/end