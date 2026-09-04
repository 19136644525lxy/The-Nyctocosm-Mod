tp @s ^-0.125 ^3.04688 ^0.21875 ~0 ~0
data modify storage animated_java Owner set from entity @s UUID
tag @s remove aj.new
function #animated_java:ajemissary/on_summon/as_locator_entities