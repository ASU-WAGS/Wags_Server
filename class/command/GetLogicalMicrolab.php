<?php

class getLogicalMicrolab extends Command
{
    public function execute(){
        $title = $_GET['id'];
        // Returns the objArray that represents this LogicalMicrolab
        $lm = LogicalMicrolab::getLogicalMicrolabById($title);
        if($lm != null){
            return JSON::success($lm->toArray());
        } else {
            return JSON::error("Logical Microlab $title does not exist on server");
        }
    }
}

?>
