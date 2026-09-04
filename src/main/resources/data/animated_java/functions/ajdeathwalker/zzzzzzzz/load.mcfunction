scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajdeathwalker.export_version dummy
scoreboard objectives add aj.ajdeathwalker.rig_loaded dummy
scoreboard objectives add aj.ajdeathwalker.animation.walk.local_anim_time dummy
scoreboard objectives add aj.ajdeathwalker.animation.slam.local_anim_time dummy
scoreboard objectives add aj.ajdeathwalker.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajdeathwalker.animation.death.local_anim_time dummy
scoreboard objectives add aj.ajdeathwalker.animation.death_alt.local_anim_time dummy
scoreboard objectives add aj.ajdeathwalker.animation.walk.loop_mode dummy
scoreboard objectives add aj.ajdeathwalker.animation.slam.loop_mode dummy
scoreboard objectives add aj.ajdeathwalker.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajdeathwalker.animation.death.loop_mode dummy
scoreboard objectives add aj.ajdeathwalker.animation.death_alt.loop_mode dummy
scoreboard players set $aj.ajdeathwalker.animation.walk aj.id 0
scoreboard players set $aj.ajdeathwalker.animation.slam aj.id 1
scoreboard players set $aj.ajdeathwalker.animation.idle aj.id 2
scoreboard players set $aj.ajdeathwalker.animation.death aj.id 3
scoreboard players set $aj.ajdeathwalker.animation.death_alt aj.id 4
scoreboard players set $aj.ajdeathwalker.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajdeathwalker.export_version aj.i 755247044
scoreboard players reset * aj.ajdeathwalker.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajdeathwalker.root] run function animated_java:ajdeathwalker/zzzzzzzz/on_load