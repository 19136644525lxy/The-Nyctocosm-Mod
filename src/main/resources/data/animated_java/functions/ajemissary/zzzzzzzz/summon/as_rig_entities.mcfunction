scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #animated_java:ajemissary/on_summon/as_rig_entities
execute if entity @s[tag=aj.ajemissary.bone] run function #animated_java:ajemissary/zzzzzzzz/on_summon/as_bones
execute if entity @s[tag=aj.ajemissary.locator_origin] run function animated_java:ajemissary/zzzzzzzz/summon/as_locator_origins
